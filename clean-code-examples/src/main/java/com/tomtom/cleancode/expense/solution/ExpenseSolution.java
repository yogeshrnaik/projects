// Problem: Following is the current implementation of the accounting system.
// The new requirement is to add a new meal type called 'Lunch'
// Your job is to apply OCP to the printReport functional, so that in future
// if you add new expense types, we should be able to do it by adding new code rather than
// modifing existing one.
// Also discover whether it is violating any other principle.

package com.tomtom.cleancode.expense.solution;

import static com.tomtom.cleancode.expense.solution.Expense.Type.BREAKFAST;
import static com.tomtom.cleancode.expense.solution.Expense.Type.CAR_RENTAL;
import static com.tomtom.cleancode.expense.solution.Expense.Type.DINNER;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class Expense {

    public enum Type {
        DINNER("Dinner", 5000, true),
        BREAKFAST("Breakfast", 1000, true),
        CAR_RENTAL("Car Rental", Integer.MAX_VALUE, false);

        private final String expenseName;
        private final int expenseLimit;
        private final boolean isMeal;

        Type(String expenseName, int expenseLimit, boolean isMeal) {
            this.expenseName = expenseName;
            this.expenseLimit = expenseLimit;
            this.isMeal = isMeal;
        }

        public boolean isMeal() {
            return isMeal;
        }
    }

    public Type type;
    public int amount;

    public Expense(Type type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getName() {
        return type.expenseName;
    }

    public boolean isMeal() {
        return type.isMeal();
    }

    public boolean isOverExpense() {
        return amount > type.expenseLimit;
    }
}

class ExpenseReportPrinter {

    public static void printReport(Expenses expenses) {
        printHeader();
        printExpenses(expenses);
        printFooter(expenses);
    }

    private static void printHeader() {
        System.out.println("Expenses " + new Date() + "\n");
    }

    private static void printExpenses(Expenses expenses) {
        expenses.getExpenses().stream().map(ExpenseReportPrinter::formatExpense).forEach(System.out::println);
    }

    private static String formatExpense(Expense expense) {
        String overExpensesMarker = expense.isOverExpense() ? "X" : " ";
        return String.format("%s\t%s\t%s", expense.getName(), expense.amount, overExpensesMarker);
    }

    private static void printFooter(Expenses expenses) {
        System.out.println("Meal expenses : " + expenses.getMealExpenses());
        System.out.println("Total expenses : " + expenses.getTotal());
    }
}

class Expenses {

    private final List<Expense> expenses;

    public Expenses(final List<Expense> expenses) {
        this.expenses = expenses.stream().collect(Collectors.toList());
    }

    public int getTotal() {
        return expenses.stream().mapToInt(e -> e.amount).sum();
    }

    public int getMealExpenses() {
        return expenses.stream().filter(Expense::isMeal).mapToInt(e -> e.amount).sum();
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }
}

public class ExpenseSolution {

    public static void main(String[] args) {
        final List<Expense> expenses = Arrays.asList(
            new Expense(DINNER, 5001),
            new Expense(DINNER, 5000),
            new Expense(BREAKFAST, 1000),
            new Expense(BREAKFAST, 1001),
            new Expense(CAR_RENTAL, 4000));
        ExpenseReportPrinter.printReport(new Expenses(expenses));
    }
}