package com.sgsc.BMS.Repositories;

import com.sgsc.BMS.models.Show;
import com.sgsc.BMS.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType,Long> {
    List<ShowSeatType> findAllByShow(Show show);

}
