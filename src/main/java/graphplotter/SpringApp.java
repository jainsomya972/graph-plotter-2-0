package graphplotter;

import graphplotter.manager.ExpressionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringApp.class, args);
        ExpressionManager manager = context.getBean(ExpressionManager.class);

        List<String> postfixExpression = manager.preEvaluate("3*x^2+2");
        Map<Double, Double> expressionOutput = manager.evaluate(postfixExpression, 1d, 10d, 0.1d);
        System.out.printf("Output for [%d] domain points: [%s]%n", expressionOutput.size(), expressionOutput);
    }
}
