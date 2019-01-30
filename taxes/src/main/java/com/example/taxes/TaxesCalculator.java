package com.example.taxes;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

public class TaxesCalculator
{

    private static final BigDecimal SCALED_ZERO = scale(ZERO);
    private static final BigDecimal FIRST_TAXES_LIMIT = scale(valueOf(817_000D));
    private static final BigDecimal SECOND_TAXES_LIMIT = scale(valueOf(1_226_000D));
    private static final BigDecimal ONE_PERCENT = scale(valueOf(0.01D));
    private static final BigDecimal TEN_PERCENT = scale(valueOf(0.10D));
    private static final BigDecimal FIFTEEN_PERCENT = scale(valueOf(0.15D));
    private static final BigDecimal SECOND_RANGE_TAXES = scale(SECOND_TAXES_LIMIT.subtract(FIRST_TAXES_LIMIT)).multiply(TEN_PERCENT);
    private static final BigDecimal ONE_HUNDRED = scale(valueOf(100D));


    private BigDecimal salary = SCALED_ZERO;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(final BigDecimal salary) {
        this.salary = scale(salary);
    }

    public BigDecimal getSocialInsurance() {
        final BigDecimal scaledSalary = scale(salary);
        return scaledSalary.equals(SCALED_ZERO)
                ? SCALED_ZERO
                : scale(scaledSalary.multiply(valueOf(0.0934D).setScale(4, CEILING)));
    }

    public BigDecimal getSocialBank() {
        final BigDecimal scaledSalary = scale(salary);
        return scaledSalary.equals(SCALED_ZERO)
                ? SCALED_ZERO
                : scaledSalary.multiply(ONE_PERCENT);
    }

    public BigDecimal getExemptSalaryTax() {
        return SCALED_ZERO;
    }

    public BigDecimal getTenPercentSalaryTax() {
        final BigDecimal scaledSalary = scale(salary);
        if (FIRST_TAXES_LIMIT.compareTo(scaledSalary) > 0) {
            return SCALED_ZERO;
        }

        if (SECOND_TAXES_LIMIT.compareTo(scaledSalary) >= 0) {
            return scale(scaledSalary.subtract(FIRST_TAXES_LIMIT)).multiply(TEN_PERCENT);
        }

        return SECOND_RANGE_TAXES;
    }

    public BigDecimal getFifteenPercentSalaryTax() {
        if (SECOND_TAXES_LIMIT.compareTo(salary) >= 0) {
            return SCALED_ZERO;
        }

        return salary.subtract(SECOND_TAXES_LIMIT).multiply(FIFTEEN_PERCENT);
    }

    public BigDecimal getSalaryTax() {
        return getExemptSalaryTax().add(getTenPercentSalaryTax()).add(getFifteenPercentSalaryTax());
    }

    public BigDecimal getTotalTax() {
        return getSocialInsurance().add(getSocialBank()).add(getSalaryTax());
    }

    public BigDecimal getNetSalary() {
        return salary.subtract(getTotalTax());
    }

    public BigDecimal getSocialInsurancePercentage() {
        return getPercentage(getSocialInsurance());
    }

    public BigDecimal getSocialBankPercentage() {
        return getPercentage(getSocialBank());
    }

    public BigDecimal getSalaryTaxPercentage() {
        return getPercentage(getSalaryTax());
    }

    public BigDecimal getTotalTaxPercentage() {
        return getPercentage(getTotalTax());
    }

    public BigDecimal getNetSalaryPercentage() {
        return getPercentage(getNetSalary());
    }

    private BigDecimal getPercentage(final BigDecimal dividend) {
        return SCALED_ZERO.equals(salary)
                ? SCALED_ZERO
                : scale(dividend.divide(salary, 8, CEILING).multiply(ONE_HUNDRED));
    }

    private static BigDecimal scale(final BigDecimal number) {
        return number.setScale(2, CEILING);
    }
}
