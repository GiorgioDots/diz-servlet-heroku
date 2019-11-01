package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
    )
public class HelloServlet extends HttpServlet {
    private JSONObject jsonObject;
    private static File file;

    @Override
    public void init() throws ServletException {
        try{
            file = new File("dictionary.json");
            if(file.createNewFile()){
                FileWriter fw = new FileWriter(file);
                fw.write("{}");
                fw.close();
            }
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(file);
            jsonObject = (JSONObject) parser.parse(reader);
        }catch(IOException e){
            throw new RuntimeException(e);
        }catch(Exception p){
            throw new RuntimeException(p);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String word = req.getParameter("word");
        if(word != null){
            returnMeaning(req,res, word);
        }else{
            PrintWriter out = res.getWriter();
            out.println("{}");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        addWord(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        modifyWord(req, res);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        removeWord(req, res);
    }

    private void returnMeaning(HttpServletRequest req, HttpServletResponse res, String word) throws IOException{
        PrintWriter out = res.getWriter();
        JSONArray result = (JSONArray) jsonObject.get(word.toLowerCase());
        JSONObject obj = new JSONObject();
        obj.put(word, result);
        out.println(obj);
        
    }

    private void addWord(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String word = req.getParameter("word");
        String meaning = req.getParameter("meaning");
        JSONObject result = new JSONObject();
        if(jsonObject.get(word) == null){
            JSONArray arr = new JSONArray();
            arr.add(meaning);
            jsonObject.put(word, arr);
            result.put(word, arr);
        }else{
            JSONArray arr = (JSONArray) jsonObject.get(word);
            arr.add(meaning);
            jsonObject.put(word, arr);
            result.put(word, arr);
        }
        FileWriter fw = new FileWriter(file);
        fw.write(jsonObject.toJSONString());
        fw.close();
        PrintWriter out = res.getWriter();
        out.println(result);
    }

    private void modifyWord(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String word = req.getParameter("word");
        String meaning = req.getParameter("meaning");
        int index = (req.getParameter("index") == null) ? Integer.parseInt(req.getParameter("index")) : 0;
        PrintWriter out = res.getWriter();
        if(jsonObject.get(word) != null){
            JSONArray arr = (JSONArray) jsonObject.get(word);
            arr.remove(index);
            arr.add(index, meaning);
            JSONObject obj = new JSONObject();
            obj.put(word, arr);
            jsonObject.replace(word.toLowerCase(), arr);
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toJSONString());
            fw.close();
            out.println(obj);
        }else{
            JSONObject obj = new JSONObject();
            obj.put("result", "word not found");
            out.println(obj);
        }
    }
    private void removeWord(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String word = req.getParameter("word");
        JSONArray oldResult = (JSONArray) jsonObject.get(word);
        JSONObject result = new JSONObject();
        PrintWriter out = res.getWriter();
        if(oldResult != null){
            jsonObject.remove(word);
            result.put(word, oldResult);
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toJSONString());
            fw.close();
        }else{
            result.put("status", "Word not found");
        }
        out.println(result);
    }

}