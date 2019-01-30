package com.example.taxes;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class RestTaxesCalculationController
{
    @GetMapping("/taxes/{salary}")
    public TaxesCalculator getTaxCalc(@PathVariable final BigDecimal salary)
    {
        final TaxesCalculator taxesCalculator = new TaxesCalculator();
        taxesCalculator.setSalary(salary);
        return taxesCalculator;
    }

    /*
    @PostMapping("/taxes")
    public TaxesCalculator setTaxCalc(@ModelAttribute final TaxesCalculator taxesCalculator)
    {
        myService.save(taxesCalculator);
        return taxesCalculator;
    }
    */
}
