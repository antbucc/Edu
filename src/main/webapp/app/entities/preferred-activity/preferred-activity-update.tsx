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
import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { getEntity, updateEntity, createEntity, reset } from './preferred-activity.reducer';

export const PreferredActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const educatorPreferences = useAppSelector(state => state.educatorPreference.entities);
  const preferredActivityEntity = useAppSelector(state => state.preferredActivity.entity);
  const loading = useAppSelector(state => state.preferredActivity.loading);
  const updating = useAppSelector(state => state.preferredActivity.updating);
  const updateSuccess = useAppSelector(state => state.preferredActivity.updateSuccess);
  const activityTypeValues = Object.keys(ActivityType);

  const handleClose = () => {
    navigate('/preferred-activity');
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
      ...preferredActivityEntity,
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
          activity: 'INDIVIDUAL',
          ...preferredActivityEntity,
          educatorPreference: preferredActivityEntity?.educatorPreference?.id,
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
                label={translate('eduApp.preferredActivity.activity')}
                id="preferred-activity-activity"
                name="activity"
                data-cy="activity"
                type="select"
              >
                {activityTypeValues.map(activityType => (
                  <option value={activityType} key={activityType}>
                    {translate('eduApp.ActivityType.' + activityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="preferred-activity-educatorPreference"
                name="educatorPreference"
                data-cy="educatorPreference"
                label={translate('eduApp.preferredActivity.educatorPreference')}
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
