package ru.petrowich.TestTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.petrowich.TestTask.models.Part;
import ru.petrowich.TestTask.services.PartService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PartController {
    private PartService partService;

    /*  поскольку после удаления и редактирования работает редирект на исходную страницу
        немного раздражает, что сбрасывается сортировка, поиск и приходится заново искать страницу
        решил сохранить значения параметров, с которыми последний раз открывалась страница со списком
    */
    private Map<String, String> listParameters = new HashMap<String, String>(); //параметры последнего вызова


    @Autowired(required = true)
    @Qualifier(value = "partService")
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @RequestMapping(value = "parts", method = RequestMethod.GET)
    public String listParts(@RequestParam Map<String, String> params, Model model) {
        model.addAttribute("part", new Part());

        setListParameters(params);

        this.partService.setCurrentPage((params.containsKey("page") ? params.get("page") : null));      //сохраняем значение номера страницы
        this.partService.setFilter((params.containsKey("filter") ? params.get("filter") : null));       //сохраняем значение поиска
        this.partService.setOrderBy((params.containsKey("orderby") ? params.get("orderby") : null));    //сохраняем поле сортировки
        this.partService.setDesc((params.containsKey("ordertype") ? params.get("ordertype") : null));             //сохраняем направление сортировки

        model.addAttribute("listParts", this.partService.findAll());                                 //вытаскиваем список деталей
        model.addAttribute("pageList", this.partService.getPageList());                              //вытаскиваем список номеров страниц
        model.addAttribute("currentPage", this.partService.getCurrentPage());                        //вытаскиваем текущую страницу
        model.addAttribute("maxPcNumber", this.partService.maxPcNumber());                           //вытаскиваем кол-во компов
        model.addAttribute("listParams", this.listParameters);                                       //вытаскиваем список параметров предыдущего вызова
        return "parts";
    }

    private void setListParameters(Map<String, String> params) {
        listParameters = new HashMap<String, String>();
        for (Map.Entry entry : params.entrySet()) {
            if (entry.getValue() != null && ((String) entry.getValue()).length() > 0) {
                listParameters.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    @RequestMapping(value = "/parts/edit", method = RequestMethod.POST)
    public String newPart(@ModelAttribute("part") Part part) {
        if(part.getPartId() == null){
            this.partService.addPart(part);
        }else {
            this.partService.updatePart(part);
        }
        //вызываем страницу parts с теми же параметрами, которыми она была открыта до того, как из неё был вызван метод addPart
        return "redirect:/parts" + getParams();
    }

    @RequestMapping("/part/{id}")
    public String editPart(@PathVariable("id") String id, Model model) {
        if (id.toLowerCase().equals("new")){
            model.addAttribute("part", new Part());
        } else {
            try {
                model.addAttribute("part", this.partService.getPartById(Integer.valueOf(id)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return "part";
    }

    @RequestMapping("/remove/{id}")
    public String removePart(@PathVariable("id") int id) {
        this.partService.removePart(id);

        //вызываем страницу parts с теми же параметрами, которыми она была открыта до того, как из неё был вызван метод removePart
        System.out.println("redirect:/parts" + getParams());
        return "redirect:/parts" + getParams();
    }

    private String getParams() {
        String params = ""; //формируем параметры get запроса

        if (listParameters.size() == 0)
            return params;


        for (Map.Entry entry : listParameters.entrySet()) {
            params = (params.length() > 0 ? params + "&" : "") + entry.getKey() + "=" + entry.getValue();
        }

        return "?" + params;
    }
}
