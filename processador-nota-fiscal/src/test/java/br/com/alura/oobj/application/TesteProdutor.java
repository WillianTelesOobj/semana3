package br.com.alura.oobj.application;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteProdutor {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
        Connection conexao = cf.createConnection();

        conexao.start();
        Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(fila);

        for (int i = 0; i< 100; i++) {
            Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
            producer.send(message);
        }

        //new Scanner(System.in).nextLine();

        session.close();
        conexao.close();
        context.close();
    }
}
