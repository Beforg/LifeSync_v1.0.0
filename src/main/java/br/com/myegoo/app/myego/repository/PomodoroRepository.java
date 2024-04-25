package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.estudo.Pomodoro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {
}
