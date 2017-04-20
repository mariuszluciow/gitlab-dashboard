package com.luciow.gitlab.dashboard.dao;

import com.luciow.gitlab.dashboard.domain.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardDao extends JpaRepository<Dashboard, Long> {

}
