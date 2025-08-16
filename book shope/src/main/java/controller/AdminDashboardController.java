package controller;

import dao.AdminDashboardDAO;
import dto.AdminSummary;

public class AdminDashboardController {

    private AdminDashboardDAO dashboardDAO = new AdminDashboardDAO();

    public AdminSummary getDashboardSummary() throws Exception {
        return dashboardDAO.getDashboardSummary();
    }
}
