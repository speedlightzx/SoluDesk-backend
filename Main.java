import static spark.Spark.*;

public class Main {
    static BancoDados banco = new BancoDados();
    public static void main(String[] args) {
    	port(8000);
    	
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");  
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });
    	
    	post("/login", (req, res) -> {
    		if(banco.TestarConexao() == false) {
    			res.status(500);
    			System.out.println("Algum erro aconteceu durante a conexão com o banco de dados.");
    			return "";
    		}
			
    		if(banco.validarLogin(req.queryParams("email"), req.queryParams("senha")) == true) {
    			res.status(200);
    			return "";
    		} else {
    			res.status(401);
    			if(req.queryParams("email").isEmpty() || req.queryParams("senha").isEmpty()) res.status(400);
    			return "";
    		}
    	});
    	
    	post("/registrar", (req, res) -> {
    		if(banco.TestarConexao() == false) {
    			res.status(500);
    			System.out.println("Algum erro aconteceu durante a conexão com o banco de dados.");
    			return "";
    		}
    		
    		if(banco.registrarUsuario(req.queryParams("email"), req.queryParams("senha")) == true) {
    			res.status(201);
    			return "";
    		} else {
    			res.status(409);
    			if(req.queryParams("email").isEmpty() || req.queryParams("senha").isEmpty()) res.status(400);
    			return "";
    		}
    	});
    	
    	post("/addHistorico", (req, res) -> {
    		if(banco.TestarConexao() == false) {
    			res.status(500);
    			System.out.println("Algum erro aconteceu durante a conexão com o banco de dados.");
    			return "";
    		}
    		
    		if(banco.adicionarHistorico(req.queryParams("data"), req.queryParams("problema")) == true) {
    			res.status(200);
    			return "";
    		} else {
    			res.status(400);
    			return "";
    		}
    	});
}
}
