package com.digitalhouse.digitalexpirience.repository;

import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE user_id = :userId AND deleted = 0 ORDER BY registration_dates DESC")
    List<Booking>findBookingByUserId(long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE experience_id = :experienceId AND deleted = 0 ORDER BY registration_dates DESC")
    List<Booking> findAllForExperienceId(Long experienceId);
}
