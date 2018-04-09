// Problem: Following is the current implementation of the accounting system.
// The new requirement is to add a new meal type called 'Lunch'
// Your job is to apply OCP to the printReport functional, so that in future
// if you add new expense types, we should be able to do it by adding new code rather than
// modifing existing one.
// Also discover whether it is violating any other principle.

package com.tomtom.cleancode.expense.solution.ocp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class Expense {

    private final String name;
    private final int amount;
    private final int expenseLimit;
    private final boolean isMeal;

    public Expense(String name, int amount, int expenseLimit, boolean isMeal) {
        this.name = name;
        this.amount = amount;
        this.isMeal = isMeal;
        this.expenseLimit = expenseLimit;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isMeal() {
        return isMeal;
    }

    public boolean isOverExpense() {
        return amount > expenseLimit;
    }
}

class Dinner extends Expense {

    public Dinner(int amount) {
        super("Dinner", amount, 5000, true);
    }
}

class Breakfast extends Expense {

    public Breakfast(int amount) {
        super("Breakfast", amount, 1000, true);
    }
}

class CarRental extends Expense {

    public CarRental(int amount) {
        super("Car Rental", amount, Integer.MAX_VALUE, false);
    }
}

class ExpenseReportPrinter {

    public static void printReport(Expenses expenses) {
        printHeader();
        printExpenses(expenses);
        printFooter(expenses);
    }

    private static void printFooter(Expenses expenses) {
        System.out.println("Meal expenses : " + expenses.getMealExpenses());
        System.out.println("Total expenses : " + expenses.getTotal());
    }

    private static void printExpenses(Expenses expenses) {
        expenses.getExpenses().stream().map(ExpenseReportPrinter::formatExpense).forEach(System.out::println);
    }

    private static String formatExpense(Expense expense) {
        String overExpensesMarker = expense.isOverExpense() ? "X" : " ";
        return String.format("%s\t%s\t%s", expense.getName(), expense.getAmount(), overExpensesMarker);
    }

    private static void printHeader() {
        System.out.println("Expenses " + new Date() + "\n");
    }
}

class Expenses {

    private final List<Expense> expenses;

    public Expenses(final List<Expense> expenses) {
        this.expenses = expenses.stream().collect(Collectors.toList());
    }

    public int getTotal() {
        return expenses.stream().mapToInt(e -> e.getAmount()).sum();
    }

    public int getMealExpenses() {
        return expenses.stream().filter(Expense::isMeal).mapToInt(e -> e.getAmount()).sum();
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }
}

public class ExpenseSolutionOCP {

    public static void main(String[] args) {
        final List<Expense> expenses = Arrays.asList(
            new Dinner(5001),
            new Dinner(5000),
            new Breakfast(1000),
            new Breakfast(1001),
            new CarRental(4000));
        ExpenseReportPrinter.printReport(new Expenses(expenses));
    }
}