package com.limikju.op.domain.dto.MemberDTO;

import java.util.Optional;

public record MemberUpdateDto(Optional<String> name, Optional<String> nickName, Optional<Integer> age) {
}
