package com.tomtom.cleancode.expense.solution.ocp.tagging;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

enum ExpenseTag {
    MEAL;
}

class Expense {

    private final String name;
    private final int amount;
    private final int expenseLimit;
    private final List<ExpenseTag> tags;

    public Expense(String name, int amount, int expenseLimit, ExpenseTag... tags) {
        this.name = name;
        this.amount = amount;
        this.tags = Arrays.asList(tags);
        this.expenseLimit = expenseLimit;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isTagged(ExpenseTag... tags) {
        return this.tags.containsAll(Arrays.asList(tags));
    }

    public boolean isOverExpense() {
        return amount > expenseLimit;
    }
}

class Dinner extends Expense {

    public Dinner(int amount) {
        super("Dinner", amount, 5000, ExpenseTag.MEAL);
    }
}

class Breakfast extends Expense {

    public Breakfast(int amount) {
        super("Breakfast", amount, 1000, ExpenseTag.MEAL);
    }
}

class CarRental extends Expense {

    public CarRental(int amount) {
        super("Car Rental", amount, Integer.MAX_VALUE);
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
        return expenses.stream().filter(e -> e.isTagged(ExpenseTag.MEAL)).mapToInt(e -> e.getAmount()).sum();
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }
}

public class ExpenseSolutionTaggingOCP {

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