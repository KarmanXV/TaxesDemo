package com.example.taxes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class TaxesController
{
    @GetMapping("/")
    public ModelAndView showCalculator()
    {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Calculator");
        modelAndView.addObject("TaxesCalculator", new TaxesCalculator());
        return modelAndView;
    }

    @PostMapping("/")
    public ModelAndView calculate(@Valid final TaxesCalculator taxesCalculator)
    {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Calculator");
        modelAndView.addObject("TaxesCalculator", taxesCalculator);
        return modelAndView;
    }
}
