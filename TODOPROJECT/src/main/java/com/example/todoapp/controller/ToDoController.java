package com.example.todoapp.controller;

import com.example.todoapp.model.ToDo;
import com.example.todoapp.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping
    public String listTodos(Model model) {
        List<ToDo> todos = toDoRepository.findAll();
        model.addAttribute("todos", todos);
        return "todo-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("todo", new ToDo());
        return "todo-form";
    }

    @PostMapping("/save")
    public String saveTodo(@ModelAttribute("todo") ToDo todo) {
        toDoRepository.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        ToDo todo = toDoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ToDo Id:" + id));
        model.addAttribute("todo", todo);
        return "todo-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        toDoRepository.deleteById(id);
        return "redirect:/todos";
    }
}
