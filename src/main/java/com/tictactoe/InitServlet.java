package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Создание новой сессии
        HttpSession currentSession = req.getSession(true);

        // Создание игрового поля
        Field field = new Field();
        Map<Integer, Sign> fieldData = field.getField();

        // Получение списка значений поля
        List<Sign> data = field.getFieldData();

        // Добавление в сессию параметров поля (нужно будет для хранения состояния между запросами)
        currentSession.setAttribute("field", field);
        // и значений поля, отсортированных по индексу (нужно для отрисовки крестиков и ноликов)
        currentSession.setAttribute("data", data);

        // Перенаправление запроса на страницу index.jsp через сервер
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    // Для того, чтоб игра имела логику, нужно сохранять состояние игры (где стоят крестики, где нолики) между
    // запросами. Самый простой метод это сделать – сохранять эти данные в сессии. При таком подходе сессия будет
    // храниться на сервере, а клиент получит идентификатор сессии в куке с именем “JSESSIONID”. Но сессию не нужно
    // создавать каждый раз, а только в начале игры. Для этого заведем еще один servlet, который назовем
    // “InitServlet”. В нем переопределим метод “doGet”, в котором создадим новую сессию, создадим игровое поле,
    // положим это игровое поле и список типа Sign в атрибуты сессии, и отправим “forward” на страницу index.jsp.
}
