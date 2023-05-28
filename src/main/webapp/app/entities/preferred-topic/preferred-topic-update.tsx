import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { getEntities as getEducatorPreferences } from 'app/entities/educator-preference/educator-preference.reducer';
import { IPreferredTopic } from 'app/shared/model/preferred-topic.model';
import { getEntity, updateEntity, createEntity, reset } from './preferred-topic.reducer';

export const PreferredTopicUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const educatorPreferences = useAppSelector(state => state.educatorPreference.entities);
  const preferredTopicEntity = useAppSelector(state => state.preferredTopic.entity);
  const loading = useAppSelector(state => state.preferredTopic.loading);
  const updating = useAppSelector(state => state.preferredTopic.updating);
  const updateSuccess = useAppSelector(state => state.preferredTopic.updateSuccess);

  const handleClose = () => {
    navigate('/preferred-topic');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEducatorPreferences({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preferredTopicEntity,
      ...values,
      educatorPreference: educatorPreferences.find(it => it.id.toString() === values.educatorPreference.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...preferredTopicEntity,
          educatorPreference: preferredTopicEntity?.educatorPreference?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.preferredTopic.home.createOrEditLabel" data-cy="PreferredTopicCreateUpdateHeading">
            <Translate contentKey="eduApp.preferredTopic.home.createOrEditLabel">Create or edit a PreferredTopic</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="preferred-topic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.preferredTopic.topicId')}
                id="preferred-topic-topicId"
                name="topicId"
                data-cy="topicId"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.preferredTopic.topic')}
                id="preferred-topic-topic"
                name="topic"
                data-cy="topic"
                type="text"
              />
              <ValidatedField
                id="preferred-topic-educatorPreference"
                name="educatorPreference"
                data-cy="educatorPreference"
                label={translate('eduApp.preferredTopic.educatorPreference')}
                type="select"
              >
                <option value="" key="0" />
                {educatorPreferences
                  ? educatorPreferences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preferred-topic" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PreferredTopicUpdate;
