package com.limikju.op.repository;

import com.limikju.op.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String userName);

    Optional<Member> findByRefreshToken(String refreshToken);
}
