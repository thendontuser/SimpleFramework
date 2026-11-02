package ru.thendont;

import ru.thendont.annotations.After;
import ru.thendont.annotations.Before;
import ru.thendont.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thendont
 * Содержит набор методов для тестирования аннотаций @Before, @Test, @After
 */
public class TestArithmetical {

    private int num1;

    private int num2;

    private HashMap<MathOperation, Integer> resultMap;

    /**
     * Инициализирует два слагаемых для последующих арифметических операций над ними
     * @param num1 первое слагаемое
     * @param num2 второе слагаемое
     */
    @Before
    public void init(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        resultMap = new HashMap<>();
    }

    /**
     * Выполняет арифметические операции над двумя слагаемыми
     */
    @Test
    public void calculate() throws Exception {
        int res;

        res = num1 + num2;
        resultMap.put(MathOperation.SUM, res);

        res = num1 - num2;
        resultMap.put(MathOperation.SUB, res);

        res = num1 * num2;
        resultMap.put(MathOperation.MUL, res);

        res = num1 / num2;
        resultMap.put(MathOperation.DIV, res);
    }

    /**
     * Выводит результат арифметических лпераций двух слагаемых в консоль
     */
    @After
    public void printResult() {
        System.out.println("============ Вывод результатов ============");
        System.out.println("Слагаемые: " + num1 + ", " + num2);

        for (Map.Entry<MathOperation, Integer> entry : resultMap.entrySet())
            System.out.println("Операция: " + entry.getKey().name() + ":   Результат: " + entry.getValue());

        System.out.println("============ Результаты выведены ============");
    }
}