package counsel.core.api.dto;

public record TeamResp(
        Long id,
        String name,
        int size // 인원수
) {}