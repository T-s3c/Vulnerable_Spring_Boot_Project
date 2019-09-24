package tk.tutorial.security.requestvuln.mapper;

import org.springframework.jdbc.core.RowMapper;
import tk.tutorial.security.requestvuln.model.Commentary;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CommentaryRowMapper implements RowMapper<Commentary> {

    @Override
    public Commentary mapRow(ResultSet rs, int rowNum) throws SQLException {
        Commentary commentary = new Commentary();
        commentary.setText(rs.getString("text"));
        commentary.setId(rs.getLong("id"));
        return commentary;
    }
}
