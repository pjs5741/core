package counsel.core.domain.member.ServiceInfo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Embeddable
public class ServiceInfo {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate; // 재직 중이면 null

    public enum Status { ACTIVE, IDLE, LEAVE } // 초록/주황/빨강

    public ServiceInfo() {}

    public ServiceInfo(Status status, LocalDate startDate, LocalDate endDate) {
        this.status = status != null ? status : Status.ACTIVE;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Status getStatus() { return status; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setStatus(Status status) { this.status = status; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    @Transient
    public String getTenureHuman() {
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        Period p = Period.between(startDate, end);
        int y = Math.max(p.getYears(), 0);
        int m = Math.max(p.getMonths(), 0);
        return (y > 0 ? y + "년 " : "") + (m > 0 ? m + "개월" : (y == 0 ? "0개월" : "")).trim();
    }
}