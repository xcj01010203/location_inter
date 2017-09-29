import com.cao.util.HttpUtils;

public class Test {
    public static final void main(String[] args) throws Exception {

        System.out.print(HttpUtils.httpGet("http://localhost:8000/static/testData.txt"));
    }
}
