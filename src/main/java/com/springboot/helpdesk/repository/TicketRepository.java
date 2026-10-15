package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.TicketInfoByExecutiveDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketNumbersByStatusDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            join t.customer c
            join t.executive e
            where c.id = ?1
        """)
    List<TicketInfoDto> getTicketByCustomerId(Long customerId, Pageable pageable);

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN c.user u
            join e.manager m
            where u.username = ?1
        """)
    List<TicketInfoDto> getTicketByCustomerUsername(String customerUsername, Pageable pageable);

    @Query("""
        select new com.springboot.helpdesk.dto.response.TicketInfoByExecutiveDto(t.id, e.id, e.name, t.subject, t.priority, t.status, t.createdAt, c.id, c.name)
        from Ticket t
        join t.customer c
        join t.executive e
        join e.user u
        where u.username = ?1
        """)
    List<TicketInfoByExecutiveDto> getTicketByExecutiveUsername(String executiveUsername, Pageable pageable);

    @Query("""
        select t.status as status, count(c.id) as numberOfCustomers
            from Ticket t join t.customer c
                group by t.status
    """)
    List<TicketNumbersByStatusDto> getTicketNumbersByStatus();

    @Query("""
        select t from Ticket t where t.priority=?1
        """)
    List<Ticket> findByPriority(Priority priority);

    @Query("select t from Ticket t where t.status = ?1")
    List<Ticket> findByStatus(Status status);

    @Query("select t from Ticket t where t.priority = ?1 and t.status = ?2")
    List<Ticket> findByPriorityAndStatus(Priority priority, Status status);

    @Query("""
        select t.id as ticketId, t.subject as subject, t.createdAt as createdAt, t.priority as priority, t.status as status
        from Ticket t
        where (?1is null or t.priority = ?1)
        and (?2 is null or t.status = ?2)
        and (?3 is null or t.createdAt >= ?3)
        and (?4 is null or t.createdAt <= ?4)
        """)
    List<TicketRespDto> dynamicFilterTicket(Priority priority, Status status, Instant instant, Instant instant1);
}
