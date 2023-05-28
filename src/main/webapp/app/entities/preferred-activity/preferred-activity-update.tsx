import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { Tool } from 'app/shared/model/enumerations/tool.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { getEntity, updateEntity, createEntity, reset } from './preferred-activity.reducer';

export const PreferredActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const preferredActivityEntity = useAppSelector(state => state.preferredActivity.entity);
  const loading = useAppSelector(state => state.preferredActivity.loading);
  const updating = useAppSelector(state => state.preferredActivity.updating);
  const updateSuccess = useAppSelector(state => state.preferredActivity.updateSuccess);
  const activityTypeValues = Object.keys(ActivityType);
  const toolValues = Object.keys(Tool);
  const difficultyValues = Object.keys(Difficulty);

  const handleClose = () => {
    navigate('/preferred-activity');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preferredActivityEntity,
      ...values,
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
          type: 'INDIVIDUAL',
          tool: 'COMPUTER',
          difficulty: 'LOW',
          ...preferredActivityEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.preferredActivity.home.createOrEditLabel" data-cy="PreferredActivityCreateUpdateHeading">
            <Translate contentKey="eduApp.preferredActivity.home.createOrEditLabel">Create or edit a PreferredActivity</Translate>
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
                  id="preferred-activity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.preferredActivity.title')}
                id="preferred-activity-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.preferredActivity.description')}
                id="preferred-activity-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.preferredActivity.type')}
                id="preferred-activity-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {activityTypeValues.map(activityType => (
                  <option value={activityType} key={activityType}>
                    {translate('eduApp.ActivityType.' + activityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.preferredActivity.tool')}
                id="preferred-activity-tool"
                name="tool"
                data-cy="tool"
                type="select"
              >
                {toolValues.map(tool => (
                  <option value={tool} key={tool}>
                    {translate('eduApp.Tool.' + tool)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.preferredActivity.difficulty')}
                id="preferred-activity-difficulty"
                name="difficulty"
                data-cy="difficulty"
                type="select"
              >
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {translate('eduApp.Difficulty.' + difficulty)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preferred-activity" replace color="info">
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

export default PreferredActivityUpdate;
