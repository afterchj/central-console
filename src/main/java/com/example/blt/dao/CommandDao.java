package com.example.blt.dao;

import com.example.blt.entity.CommandInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hongjian.chen
 * @date 2019/6/27 11:27
 */
public interface CommandDao extends JpaRepository<CommandInfo, Integer> {
}
