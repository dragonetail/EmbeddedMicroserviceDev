package com.github.dragonetail.model;

import com.github.dragonetail.model.converter.HashMapConverter;
import com.github.dragonetail.model.converter.SetStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * OAuth2认证客户端
 *
 * @author sunyx
 */
@Data
@Entity
public class OauthClient implements Serializable {
    private static final long serialVersionUID = -7078116681946969969L;

    /**
     * 客户端ID
     */
    @Id
    @Size(max = 64)
    @Column(nullable = false, unique = true)
    private String clientId;

    /**
     * 秘钥
     */
    @NotEmpty
    @Size(max = 255)
    private String clientSecret;

    /**
     * 资源ID列表
     */
    //@NotEmpty
    @Convert(converter = SetStringConverter.class)
    private Set<String> resourceIds = new HashSet<>();

    /**
     * 是否需要秘钥
     */
    private boolean secretRequired;

    /**
     * 是否使用Scope
     */
    private boolean scoped;

    /**
     * Scope列表
     */
    @Convert(converter = SetStringConverter.class)
    private Set<String> scope = new HashSet<>();

    /**
     * 授权类型
     */
    @Convert(converter = SetStringConverter.class)
    private Set<String> authorizedGrantTypes = new HashSet<>();

    /**
     * 注册地址
     */
    @Convert(converter = SetStringConverter.class)
    private Set<String> registeredRedirectUri = new HashSet<>();

    /**
     * 授权列表
     */
    @Convert(converter = SetStringConverter.class)
    private Set<String> authorities = new HashSet<>();

    /**
     * 访问Token有效期间（秒）
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新Token有效期间（秒）
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 自动同意授权
     */
    private boolean autoApprove;

    /**
     * 附加信息
     */
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> additionalInformation = new HashMap<>();
}