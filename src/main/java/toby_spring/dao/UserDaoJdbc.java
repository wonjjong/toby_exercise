package toby_spring.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import toby_spring.domain.Level;
import toby_spring.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLevel(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));
                    return user;
                }
            };


    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) " +
                "values(?,?,?,?,?,?)",
                user.getId(), user.getName(), user.getPassword(),
                user.getLevel().intValue(),user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
//        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
//                new Object[] {id}, this.userMapper);
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", this.userMapper, id);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
//        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ? , password = ? , level = ? , login = ?, "+
                "recommend = ? where id = ? ",user.getName(), user.getPassword(), user.getLevel().intValue(),
                user.getLogin(), user.getRecommend(), user.getId()
                );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }

}
