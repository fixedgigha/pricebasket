package pricebasket;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    ApplicationContext ctx;

    @Test
    public void testRun() throws Exception{
        CommandLineRunner cmdLine = ctx.getBean(CommandLineRunner.class);
        cmdLine.run("Stuff1", "Stuff2");
        String output = capture.toString();
        Assert.assertThat(output, containsString("Subtotal: £1.89"));
        Assert.assertThat(output, containsString("(no offers available)"));
        Assert.assertThat(output, containsString("Total: £1.89"));
    }

}
