package ru.thendont;

import ru.thendont.annotations.After;
import ru.thendont.annotations.Before;
import ru.thendont.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author thendont
 * Класс, предназначенный для запуска тестового класса
 */
public class TestRunner {

    private static final Logger logger = Logger.getLogger(TestRunner.class.getName());

    private final TestArithmetical testArithmetical;

    private final Class<?> testedClass;

    private List<Method> methods;

    private int fallsTests = 0;

    public TestRunner(Class<?> testedClass) {
        testArithmetical = new TestArithmetical();
        this.testedClass = testedClass;
        methods = new ArrayList<>();
    }

    /**
     * Выполняет методы тестового класса, помчеченных анотацией @Before
     */
    private void doBeforeMethods() {
        logger.info("Запуск TestRunner.doBeforeMethods...");

        for (Method m : methods) {
            if (m.isAnnotationPresent(Before.class))
                try {
                    logger.info("Запуск метода " + m.getName());
                    m.invoke(testArithmetical, 53, 78);
                } catch (Exception ex) {
                    logger.info("Exception in TestRunner.doBeforeMethods: " + ex.getMessage());
                    fallsTests++;
                }
        }

        logger.info("Конец TestRunner.doBeforeMethods...");
    }

    /**
     * Выполняет методы тестового класса, помчеченных анотацией @Test
     */
    private void doTestMethods() {
        logger.info("Запуск TestRunner.doTestMethods...");

        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class))
                try {
                    logger.info("Запуск метода " + m.getName());
                    m.invoke(testArithmetical);
                } catch (Exception ex) {
                    logger.info("Exception in TestRunner.doTestMethods: " + ex.getMessage());
                    fallsTests++;
                }
        }

        logger.info("Конец TestRunner.doTestMethods...");
    }

    /**
     * Выполняет методы тестового класса, помчеченных анотацией @After
     */
    private void doAfterMethods() {
        logger.info("Запуск TestRunner.doAfterMethods...");

        for (Method m : methods) {
            if (m.isAnnotationPresent(After.class))
                try {
                    logger.info("Запуск метода " + m.getName());
                    m.invoke(testArithmetical);
                } catch (Exception ex) {
                    logger.info("Exception in TestRunner.doAfterMethods: " + ex.getMessage());
                    fallsTests++;
                }
        }

        logger.info("Конец TestRunner.doAfterMethods...");
    }

    /**
     * Поочередно выполняет методы из класса testedClass
     */
    public void run() {
        int totalTests = 0;

        logger.info("Получен для запуска тестовый класс " + testedClass.getCanonicalName());

        methods = Arrays.stream(testedClass.getMethods()).toList();

        for (Method m : methods)
            if (m.isAnnotationPresent(Before.class) || m.isAnnotationPresent(Test.class) || m.isAnnotationPresent(After.class))
                totalTests++;

        doBeforeMethods();
        doTestMethods();
        doAfterMethods();

        logger.info("Статистика\nПрошло успешно: " + (totalTests - fallsTests) + "\nУпало: " + fallsTests + "\nВсего: " + totalTests);

        logger.info("Конец запуска тестового класа");
    }
}