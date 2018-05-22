import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import ru.miet.orgact.Article;

public class TestArticle {


    //тест для проверки преобразование оюъекта в json
    @Test
    public void testArticle() {
        //arange
        //создаём пустой объект класса публикация
        Article testArticle = new Article();

        //тестовые название, страна и город
        String name = "test name";
        String city = "test city";
        String country = "test country";

        //устанавливаем тестовые значения для публикации
        testArticle.setName(name);
        testArticle.setCity(city);
        testArticle.setCountry(country);

        //act
        //преобразуем объект в json
        String result = testArticle.toJSON();


        //assert
        //проверяем корректность преобразования

        Gson gson = new Gson();
        Article fromJson = gson.fromJson(result, Article.class);

        Assert.assertEquals(fromJson.getName(), name);
        Assert.assertEquals(fromJson.getCity(), city);
        Assert.assertEquals(fromJson.getCountry(), country);
    }

}
