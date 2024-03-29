package player.tingxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by c_pse on 2016/5/20.
 */

public class Days2TodayBuilder {
    protected final String TAG = "Days2TodayBuilder";

    public List<Memo> buildFromAssetFile(Context context, String filename){
        InputStream is=null;
        try{
            // 1. open asset file: preset_person.xml
            is = context.getAssets().open(filename);

            // 2. create xpath object
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =  builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(is);

            XPath xPath =  XPathFactory.newInstance().newXPath();

            // 3. query memo node_list with "daystonow" attribute
            String memo_expression = "//person/memo[type[@id='daystonow']]";
            NodeList memo_node_list = (NodeList)xPath.compile(memo_expression).evaluate(xmlDocument, XPathConstants.NODESET);
            List<Memo> memo_list = new ArrayList<>();

            for(int i = 0; i < memo_node_list.getLength(); i++){
                Node memo_node = memo_node_list.item(i);
                // a. get drawable image
                String expr = "parent::person/image";
                String strValue = xPath.compile(expr).evaluate(memo_node);
                Bitmap image_bitmap = BitmapFactory.decodeStream(context.getAssets().open(strValue));
                image_bitmap.setDensity(Bitmap.DENSITY_NONE);
                Drawable image = new BitmapDrawable(context.getResources(),image_bitmap);

                // b. get date
                expr = "date";
                strValue = xPath.compile(expr).evaluate(memo_node);

                // c. calc days to today
                String days = Days2TodayCalculator.getString(strValue);

                // d. get hint template
                expr = "type[@id='daystonow']/@hint";
                strValue = xPath.compile(expr).evaluate(memo_node);

                // e. replace $placeholder$ with days
                strValue = strValue.replaceAll("\\$placeholder\\$", days);
//                strValue += " asda sda sdasd asdasd asdasdsad qweqweq weqweq weq weqw ewq eqweq weq wew q e q w e";
//                strValue += " asda sda sdasd asdasd asdasdsad qweqweq weqweq weq weqw ewq eqweq weq wew q e q w e";
//                strValue += " asda sda sdasd asdasd asdasdsad qweqweq weqweq weq weqw ewq eqweq weq wew q e q w e";

                // f. new memo and add it to list
                Memo memo = new Memo(strValue, image);
                memo_list.add(memo);
            }

            return memo_list;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null)
                {
                    is.close();
                }
            } catch (Exception ignored) {}
        }


        return null;
    }
}
