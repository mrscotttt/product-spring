package com.example.productspring.repository;

import com.example.productspring.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbc;

    private static final RowMapper<User> ROW_MAPPER = (rs, i) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setName(rs.getString("name"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setWebsite(rs.getString("website"));
        return u;
    };

    public List<User> findAll() {
        return jdbc.query("SELECT * FROM users", ROW_MAPPER);
    }

    public Optional<User> findById(Long id) {
        return jdbc.query("SELECT * FROM users WHERE id = ?", ROW_MAPPER, id).stream().findFirst();
    }

    public User save(User user) {
        String sql = """
            INSERT INTO users (name, username, email, phone, website)
            VALUES (?, ?, ?, ?, ?)
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getWebsite());
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    public User update(Long id, User user) {
        String sql = """
            UPDATE users
            SET name = ?, username = ?, email = ?, phone = ?, website = ?
            WHERE id = ?
            """;
        jdbc.update(sql,
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getWebsite(),
            id
        );
        user.setId(id);
        return user;
    }

    public void delete(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}
