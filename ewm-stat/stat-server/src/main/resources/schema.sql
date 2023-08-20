create table if not exists endpoint_hits (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(255) NOT NULL,
    ip VARCHAR(31) NOT NULL,
    uri VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL
);

CREATE INDEX idx_endpoint_hits_app ON endpoint_hits (app);
CREATE INDEX idx_endpoint_hits_ip ON endpoint_hits (ip);
CREATE INDEX idx_endpoint_hits_uri ON endpoint_hits (uri);
CREATE INDEX idx_endpoint_hits_created ON endpoint_hits (created);