/*
 * (C) Copyright 2017-2018 OpenVidu (https://openvidu.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.openvidu.server.core;

import com.google.gson.JsonObject;

public class Participant {

	private String participantPrivatetId; // ID to identify the user on server (org.kurento.jsonrpc.Session.id)
	private String participantPublicId; // ID to identify the user on clients
	private Long createdAt; // Timestamp when this connection was established
	private String clientMetadata = ""; // Metadata provided on client side
	private String serverMetadata = ""; // Metadata provided on server side
	private Token token; // Token associated to this participant
	private String location; // Remote IP of the participant
	private String platform; // Platform used by the participant to connect to the session

	protected boolean streaming = false;
	protected volatile boolean closed;

	private final String METADATA_SEPARATOR = "%/%";

	public Participant(String participantPrivatetId, String participantPublicId, Token token, String clientMetadata,
			String location, String platform) {
		this.participantPrivatetId = participantPrivatetId;
		this.participantPublicId = participantPublicId;
		this.createdAt = System.currentTimeMillis();
		this.token = token;
		this.clientMetadata = clientMetadata;
		if (!token.getServerMetadata().isEmpty())
			this.serverMetadata = token.getServerMetadata();
		this.location = location;
		this.platform = platform;
	}

	public String getParticipantPrivateId() {
		return participantPrivatetId;
	}

	public void setParticipantPrivateId(String participantPrivateId) {
		this.participantPrivatetId = participantPrivateId;
	}

	public String getParticipantPublicId() {
		return participantPublicId;
	}

	public void setParticipantPublicId(String participantPublicId) {
		this.participantPublicId = participantPublicId;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public String getClientMetadata() {
		return clientMetadata;
	}

	public void setClientMetadata(String clientMetadata) {
		this.clientMetadata = clientMetadata;
	}

	public String getServerMetadata() {
		return serverMetadata;
	}

	public void setServerMetadata(String serverMetadata) {
		this.serverMetadata = serverMetadata;
	}

	public Token getToken() {
		return this.token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public boolean isStreaming() {
		return streaming;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setStreaming(boolean streaming) {
		this.streaming = streaming;
	}

	public String getPublisherStreamId() {
		return null;
	}

	public String getFullMetadata() {
		String fullMetadata;
		if ((!this.clientMetadata.isEmpty()) && (!this.serverMetadata.isEmpty())) {
			fullMetadata = this.clientMetadata + METADATA_SEPARATOR + this.serverMetadata;
		} else {
			fullMetadata = this.clientMetadata + this.serverMetadata;
		}
		return fullMetadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (participantPrivatetId == null ? 0 : participantPrivatetId.hashCode());
		result = prime * result + (streaming ? 1231 : 1237);
		result = prime * result + (participantPublicId == null ? 0 : participantPublicId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Participant)) {
			return false;
		}
		Participant other = (Participant) obj;
		if (participantPrivatetId == null) {
			if (other.participantPrivatetId != null) {
				return false;
			}
		} else if (!participantPrivatetId.equals(other.participantPrivatetId)) {
			return false;
		}
		if (streaming != other.streaming) {
			return false;
		}
		if (participantPublicId == null) {
			if (other.participantPublicId != null) {
				return false;
			}
		} else if (!participantPublicId.equals(other.participantPublicId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (participantPrivatetId != null) {
			builder.append("participantPrivateId=").append(participantPrivatetId).append(", ");
		}
		if (participantPublicId != null) {
			builder.append("participantPublicId=").append(participantPublicId).append(", ");
		}
		builder.append("streaming=").append(streaming).append("]");
		return builder.toString();
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("connectionId", this.participantPublicId);
		json.addProperty("createdAt", this.createdAt);
		json.addProperty("location", this.location);
		json.addProperty("platform", this.platform);
		json.addProperty("token", this.token.getToken());
		json.addProperty("role", this.token.getRole().name());
		json.addProperty("serverData", this.serverMetadata);
		json.addProperty("clientData", this.clientMetadata);
		return json;
	}

}
