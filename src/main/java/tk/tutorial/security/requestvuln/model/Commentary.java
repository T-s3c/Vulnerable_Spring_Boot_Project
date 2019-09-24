package tk.tutorial.security.requestvuln.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    public Commentary(String text) {
        this.text = text;
    }
}
