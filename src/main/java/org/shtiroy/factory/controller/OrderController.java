package org.shtiroy.factory.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shtiroy.factory.entity.*;
import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.report.PdfCreate;
import org.shtiroy.factory.repository.*;
import org.shtiroy.factory.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class OrderController {

    public static final Logger LOGGER = LogManager.getLogger(OrderController.class.getName());
    @Autowired
    public ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleTypeRepository moduleTypeRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private ConsumableRepository consumableRepository;
    @Autowired
    private PdfCreate pdfCreate;
    @Value("${factory.files}")
    private String filesPath;

    @GetMapping("/order/order_list")
    public String orderList(Model model){
        LOGGER.info("get order lost");
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders",orders);
        return "/order/order_list";
    }

    @GetMapping("/order/order_add")
    public String openOrderAdd(Model model){

        List<Shop> shops = shopRepository.findByShopType(1);
        List<Product> products = productRepository.findAllByStatus(true);

        model.addAttribute("shops", shops);
        model.addAttribute("products",products);
        return "/order/order_add";
    }

    @PostMapping("/order/order_save")
    public String orderSave(@ModelAttribute("order") String orderBody, Authentication authentication){
        LOGGER.info("call orderSave");
        JSONObject jsonObject = new JSONObject(orderBody);
        JSONObject jsonClient = jsonObject.getJSONObject("client");
        JSONObject jsonOrder = jsonObject.getJSONObject("order");
        LOGGER.info("save client info");
        Client client = new Client();
        client.setClientName(jsonClient.getString("client_name"));
        client.setClientAddress(jsonClient.getString("client_address"));
        client.setClientPhone(jsonClient.getString("client_phone"));
        client = clientRepository.save(client);
        LOGGER.info("save order info");
        Order order = new Order();
        order.setNumberFactory(jsonOrder.getString("number_factory"));
        order.setNumberShop(jsonOrder.getString("number_shop"));
        order.setDateRegistration(DateUtils.strToDate(jsonOrder.getString("date_registration")));
        order.setDateProduction(DateUtils.strToDate(jsonOrder.getString("date_production")));
        order.setSupplement(jsonOrder.getString("supplement"));
        order.setIdShop(shopRepository.findById(jsonOrder.getInt("id_shop")).get());
        order.setIdClient(client);
        order = orderRepository.save(order);
        LOGGER.info("save order_product");
        Product product = productRepository.getOne(jsonObject.getInt("id"));
        OrderProduct orderProduct = new OrderProduct(order, product);
        orderProduct = orderProductRepository.save(orderProduct);
        LOGGER.info("save modules to order composition");
        Composition composition = null;
        JSONArray moduleTypeList = jsonObject.getJSONArray("moduleTypeList");
        for (int i = 0; i < moduleTypeList.length(); i++){
            Module module = moduleRepository.findById(moduleTypeList.getJSONObject(i).getInt("idModule")).get();
            ModuleType moduleType = moduleTypeRepository.findById(moduleTypeList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,null,null,moduleType);
            compositionRepository.save(composition);
        }
        JSONArray accessoryList = jsonObject.getJSONArray("accessoryList");
        for (int i = 0; i < accessoryList.length(); i++){
            Module module = moduleRepository.findById(accessoryList.getJSONObject(i).getInt("idModule")).get();
            Accessory accessory = accessoryRepository.findById(accessoryList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,accessory,null,null);
            compositionRepository.save(composition);
        }
        JSONArray consumableList = jsonObject.getJSONArray("consumableList");
        for (int i = 0; i < consumableList.length(); i++){
            Module module = moduleRepository.findById(consumableList.getJSONObject(i).getInt("idModule")).get();
            Consumable consumable = consumableRepository.findById(consumableList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,null,consumable,null);
            compositionRepository.save(composition);
        }
        LOGGER.info(product.toString());
        UserLog userLog = new UserLog(authentication,"SAVE ORDER",orderBody);
        userLogRepository.save(userLog);
        return "/order/order_list";
    }

    @GetMapping("/order/print/{idOrder}")
    public void  getOrderReport(@PathVariable("idOrder") String idOrderStr, Authentication authentication,
                                HttpServletResponse response){
        LOGGER.info("idOrder " + idOrderStr);
        int idOrder = Integer.parseInt(idOrderStr);
        Order order = orderRepository.getOne(idOrder);
        List<Composition> compositions = compositionRepository.findAllComposition(order.getId());
        Document orderPDf = new Document();
        try {
            PdfWriter.getInstance(orderPDf, new FileOutputStream(filesPath+"reports\\"+idOrderStr+".pdf"));
            orderPDf.open();
            pdfCreate.logo(orderPDf);
            Paragraph paragraphOrder = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraphOrder,"Дата оформления: ");
            pdfCreate.paragraphAddItalicText(paragraphOrder,order.getDateRegistration().toString()+" ");
            pdfCreate.paragraphAddNormalText(paragraphOrder,"Заказчик: ");
            pdfCreate.paragraphAddItalicText(paragraphOrder,order.getIdShop().getShopName()+" ");
            pdfCreate.paragraphAddNormalText(paragraphOrder,"Дата изготовления: ");
            pdfCreate.paragraphAddItalicText(paragraphOrder,order.getDateProduction().toString()+" ");
            orderPDf.add(paragraphOrder);
            orderPDf.add(pdfCreate.addEmptyLine(2));
            int count = 0;
            Map<Module, List<ModuleType>> mapModuleType = new LinkedHashMap<>();
            Map<Module, List<Object>> mapConsumable = new LinkedHashMap<>();
            Map<Module, List<Object>> mapAccessory = new LinkedHashMap<>();
            for (Composition composition : compositions){
                if (composition.getIdModuleType() != null)
                    mapModuleType.computeIfAbsent(composition.getIdModule(), k -> new ArrayList<>()).add(composition.getIdModuleType());
                if (composition.getIdConsumable() != null)
                    mapConsumable.computeIfAbsent(composition.getIdModule(), k -> new ArrayList<>()).add(composition.getIdConsumable());
                if (composition.getIdAccessory() != null)
                    mapAccessory.computeIfAbsent(composition.getIdModule(), k -> new ArrayList<>()).add(composition.getIdAccessory());
            }
            for (Map.Entry<Module, List<ModuleType>> entry : mapModuleType.entrySet()) {
                count++;
                Paragraph paragraph = new Paragraph();
                pdfCreate.paragraphAddNormalText(paragraph, count+". ");
                pdfCreate.paragraphAddNormalText(paragraph, entry.getKey().toString()+": ");
                orderPDf.add(paragraph);
                for (ModuleType object : entry.getValue()) {
                    Paragraph paragraphSub = new Paragraph();
                    paragraphSub.setFirstLineIndent(40);
                    pdfCreate.paragraphAddItalicText(paragraphSub, "- "+object.toString());
                    if (object.getIdPhoto() != null) {
                        pdfCreate.paragraphAddImage(paragraphSub,object.getIdPhoto().getPhotoPath());
                    }
                    orderPDf.add(paragraphSub);
                }
            }
            for (Map.Entry<Module, List<Object>> entry : mapConsumable.entrySet()) {
                count++;
                Paragraph paragraph = new Paragraph();
                pdfCreate.paragraphAddNormalText(paragraph, count+". ");
                pdfCreate.paragraphAddNormalText(paragraph, entry.getKey().toString()+": ");
                orderPDf.add(paragraph);
                for (Object object : entry.getValue()) {
                    Paragraph paragraphSub = new Paragraph();
                    paragraphSub.setFirstLineIndent(40);
                    pdfCreate.paragraphAddItalicText(paragraphSub, "- "+object.toString());
                    orderPDf.add(paragraphSub);
                }
            }
            for (Map.Entry<Module, List<Object>> entry : mapAccessory.entrySet()) {
                count++;
                Paragraph paragraph = new Paragraph();
                pdfCreate.paragraphAddNormalText(paragraph, count+". ");
                pdfCreate.paragraphAddNormalText(paragraph, entry.getKey().toString()+": ");
                orderPDf.add(paragraph);
                for (Object object : entry.getValue()) {
                    Paragraph paragraphSub = new Paragraph();
                    paragraphSub.setFirstLineIndent(40);
                    pdfCreate.paragraphAddItalicText(paragraphSub, "- "+object.toString());
                    orderPDf.add(paragraphSub);
                }
            }
            //LOGGER.info(moduleTypeObjectMap);
            /*for (Composition composition : compositions){
                count++;
                Paragraph paragraph = new Paragraph();
                pdfCreate.paragraphAddNormalText(paragraph, count+". ");
                pdfCreate.paragraphAddNormalText(paragraph, composition.getIdModule().toString()+": ");
                if (composition.getIdAccessory() != null) {
                    pdfCreate.paragraphAddItalicText(paragraph, composition.getIdAccessory().toString());
                }
                if (composition.getIdConsumable() != null){
                    pdfCreate.paragraphAddItalicText(paragraph,composition.getIdConsumable().toString());
                }
                if (composition.getIdModuleType() != null){
                    pdfCreate.paragraphAddItalicText(paragraph, composition.getIdModuleType().toString());
                    if (composition.getIdModuleType().getIdPhoto() != null) {
                        pdfCreate.paragraphAddImage(paragraph,composition.getIdModuleType().getIdPhoto().getPhotoPath());
                    }
                }
                orderPDf.add(paragraph);
            }*/
            orderPDf.add(pdfCreate.addEmptyLine(2));
            Paragraph paragraph = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraph,"Дополнения: ");
            pdfCreate.paragraphAddItalicText(paragraph, order.getSupplement());
            orderPDf.add(paragraph);
            orderPDf.add(pdfCreate.addEmptyLine(1));
            paragraph = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraph,"Контактный телефон покупателя: ");
            pdfCreate.paragraphAddItalicText(paragraph,order.getIdClient().getClientPhone());
            orderPDf.add(paragraph);
            orderPDf.add(pdfCreate.addEmptyLine(1));
            paragraph = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraph,"Адрес доставки покупателя: ");
            pdfCreate.paragraphAddItalicText(paragraph,order.getIdClient().getClientAddress());
            orderPDf.add(paragraph);
            orderPDf.add(pdfCreate.addEmptyLine(1));
            paragraph = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraph,"Подпись продавца: ");
            pdfCreate.paragraphAddItalicText(paragraph,"                                    ");
            pdfCreate.paragraphAddNormalText(paragraph,"Подпись покупателя: ");
            pdfCreate.paragraphAddItalicText(paragraph,"                                    ");
            orderPDf.add(paragraph);
            orderPDf.add(pdfCreate.addEmptyLine(1));
            paragraph = new Paragraph();
            pdfCreate.paragraphAddNormalText(paragraph, "Магазинный номер:");
            pdfCreate.paragraphAddItalicText(paragraph, order.getNumberShop()+"  ");
            pdfCreate.paragraphAddNormalText(paragraph, "Фабричный номер:");
            pdfCreate.paragraphAddItalicText(paragraph, order.getNumberFactory());
            orderPDf.add(paragraph);
        } catch (FileNotFoundException ex){
            LOGGER.info("FILE not EXIST");
        } catch (DocumentException ex){
            LOGGER.error(ex.getMessage());
        }
        orderPDf.close();
        try {
            File initialFile = new File( filesPath+"reports\\"+idOrderStr+".pdf");
            InputStream is = new FileInputStream(initialFile);
            response.setContentType("application/pdf");
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            LOGGER.info("Error writing file to output stream. Filename was '{}'", idOrderStr, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
        UserLog userLog = new UserLog(authentication,"PRINT ORDER",idOrderStr);
        userLogRepository.save(userLog);
    }
}
