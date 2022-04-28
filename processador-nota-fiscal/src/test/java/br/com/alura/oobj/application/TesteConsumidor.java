package br.com.alura.oobj.application;

import java.util.Scanner;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidor {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
        Connection conexao = cf.createConnection();

        conexao.start();
        Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage)message;

                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        new Scanner(System.in).nextLine();

        session.close();
        conexao.close();
        context.close();
    }
}
