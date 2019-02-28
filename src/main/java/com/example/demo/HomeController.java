package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    ToDoListRepository toDoListRepository;

    @RequestMapping("/")
    public String listToDoList(Model model) {
        model.addAttribute("toDoLists", toDoListRepository.findAll());
        return "todolist";
    }

    @GetMapping("/add")
    public String listForm(Model model) {
        model.addAttribute("toDoList", new ToDoList());
        return "listform";
    }

    @PostMapping("/process")
    public String processForm(@Valid ToDoList toDoList,
                              BindingResult result, @RequestParam("dueDate") String dueDate) {
        if (result.hasErrors()) {
            return "listform";
        }

        Date date = new Date();

        try {
            date = new SimpleDateFormat("yyyy-MM-d").parse(dueDate);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        toDoList.setDueDate(date);
        toDoListRepository.save(toDoList);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showToDoList(@PathVariable("id") long id, Model model) {
        model.addAttribute("toDoList", toDoListRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateToDoList(@PathVariable("id") long id, Model model) {
        model.addAttribute("toDoList", toDoListRepository.findById(id).get());
        return "listform";
    }

    @RequestMapping("/delete/{id}")
    public String delToDoList(@PathVariable("id") long id) {
        toDoListRepository.deleteById(id);
        return "redirect:/";
    }
}

