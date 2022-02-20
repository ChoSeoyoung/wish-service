package hello.wishservice.web.wish.basic;

import hello.wishservice.domain.wish.Wish;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {
    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Wish wish = new Wish();
        wish.setTitle("  ");
        wish.setPeriod("  ");
        wish.setCost(-1);

        Set<ConstraintViolation<Wish>> violations = validator.validate(wish);
        for (ConstraintViolation<Wish> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.message = " + violation.getMessage());
        }
    }
}
