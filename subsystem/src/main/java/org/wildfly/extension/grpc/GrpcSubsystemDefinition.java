/*
 *  Copyright 2022 Red Hat
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wildfly.extension.grpc;

import java.util.Collection;
import java.util.List;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.validation.IntRangeValidator;
import org.jboss.as.controller.operations.validation.ModelTypeValidator;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

public class GrpcSubsystemDefinition extends PersistentResourceDefinition {

    static final SimpleAttributeDefinition GRPC_FLOW_CONTROL_WINDOW = new SimpleAttributeDefinitionBuilder(
            "flow-control-window", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_HANDSHAKE_TIMEOUT = new SimpleAttributeDefinitionBuilder(
            "handshake-timeout", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_INITIAL_FLOW_CONTROL_WINDOW = new SimpleAttributeDefinitionBuilder(
            "initial-flow-control-window", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_KEEP_ALIVE_TIME = new SimpleAttributeDefinitionBuilder(
            "keep-alive-time", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_KEEP_ALIVE_TIMEOUT = new SimpleAttributeDefinitionBuilder(
            "keep-alive-timeout", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_KEY_MANAGER_NAME = new SimpleAttributeDefinitionBuilder(
            "key-manager-name", ModelType.STRING)
            .setAllowExpression(true)
            .setDefaultValue(new ModelNode("applicationKM"))
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new ModelTypeValidator(ModelType.STRING, false))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_CONCURRENT_CALLS_PER_CONNECTION = new SimpleAttributeDefinitionBuilder(
            "max-concurrent-calls-per-connection", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_CONNECTION_AGE = new SimpleAttributeDefinitionBuilder(
            "max-connection-age", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_CONNECTION_AGE_GRACE = new SimpleAttributeDefinitionBuilder(
            "max-connection-age-grace", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_CONNECTION_IDLE = new SimpleAttributeDefinitionBuilder(
            "max-connection-idle", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_INBOUND_MESSAGE_SIZE = new SimpleAttributeDefinitionBuilder(
            "max-inbound-message-size", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_MAX_INBOUND_METADATA_SIZE = new SimpleAttributeDefinitionBuilder(
            "max-inbound-metadata-size", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_PERMIT_KEEP_ALIVE_TIME = new SimpleAttributeDefinitionBuilder(
            "permit-keep-alive-time", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, false, true))
            .build();

    static final SimpleAttributeDefinition GRPC_PERMIT_KEEP_ALIVE_WITHOUT_CALLS = new SimpleAttributeDefinitionBuilder(
            "permit-keep-alive-without-calls", ModelType.BOOLEAN)
            .setAllowExpression(true)
            .setDefaultValue(null)
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new ModelTypeValidator(ModelType.BOOLEAN, false))
            .build();

    static final SimpleAttributeDefinition GRPC_SERVER_HOST = new SimpleAttributeDefinitionBuilder(
            "server-host", ModelType.STRING)
            .setAllowExpression(true)
            .setDefaultValue(new ModelNode("localhost"))
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new ModelTypeValidator(ModelType.STRING, false))
            .build();

    static final SimpleAttributeDefinition GRPC_SERVER_PORT = new SimpleAttributeDefinitionBuilder(
            "server-port", ModelType.INT)
            .setAllowExpression(true)
            .setDefaultValue(new ModelNode(9555))
            .setRequired(false)
            .setRestartAllServices()
            .setValidator(new IntRangeValidator(0, 65535, false, true))
            .build();

    static final List<AttributeDefinition> ATTRIBUTES = List.of(
            GRPC_FLOW_CONTROL_WINDOW,
            GRPC_HANDSHAKE_TIMEOUT,
            GRPC_INITIAL_FLOW_CONTROL_WINDOW,
            GRPC_KEEP_ALIVE_TIME,
            GRPC_KEEP_ALIVE_TIMEOUT,
            GRPC_KEY_MANAGER_NAME,
            GRPC_MAX_CONCURRENT_CALLS_PER_CONNECTION,
            GRPC_MAX_CONNECTION_AGE,
            GRPC_MAX_CONNECTION_AGE_GRACE,
            GRPC_MAX_CONNECTION_IDLE,
            GRPC_MAX_INBOUND_MESSAGE_SIZE,
            GRPC_MAX_INBOUND_METADATA_SIZE,
            GRPC_PERMIT_KEEP_ALIVE_TIME,
            GRPC_PERMIT_KEEP_ALIVE_WITHOUT_CALLS,
            GRPC_SERVER_HOST,
            GRPC_SERVER_PORT);

    // This must be initialized last to ensure the other static attributes are created first
    static final GrpcSubsystemDefinition INSTANCE = new GrpcSubsystemDefinition();

    public GrpcSubsystemDefinition() {
        super(new SimpleResourceDefinition.Parameters(Paths.SUBSYSTEM, GrpcExtension.getResolver())
                .setAddHandler(GrpcSubsystemAdd.INSTANCE)
                .setRemoveHandler(ReloadRequiredRemoveStepHandler.INSTANCE));
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return ATTRIBUTES;
    }
}
