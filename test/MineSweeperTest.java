import com.company.MineSweeper;
import com.company.MineSweeperImpl;
import org.junit.Assert;
import org.junit.Test;

public class MineSweeperTest {

    @Test
    public void shouldSetMineField() {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField("*...\n..*.\n....");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidInputCaseFirst() {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField("*..6.\n..*.\n....");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidInputCaseSecond() {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField("*......\n..*.\n....");
    }

    @Test
    public void getHintField() {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField("*...\n..*.\n....\n");
        String hintField = mineSweeper.getHintField();
        Assert.assertEquals("*211\n12*1\n0111\n", hintField);
    }
}
