import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducator } from 'app/shared/model/educator.model';
import { getEntities as getEducators } from 'app/entities/educator/educator.reducer';
import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { getEntity, updateEntity, createEntity, reset } from './educator-preference.reducer';

export const EducatorPreferenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const educators = useAppSelector(state => state.educator.entities);
  const educatorPreferenceEntity = useAppSelector(state => state.educatorPreference.entity);
  const loading = useAppSelector(state => state.educatorPreference.loading);
  const updating = useAppSelector(state => state.educatorPreference.updating);
  const updateSuccess = useAppSelector(state => state.educatorPreference.updateSuccess);
  const difficultyValues = Object.keys(Difficulty);

  const handleClose = () => {
    navigate('/educator-preference');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEducators({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...educatorPreferenceEntity,
      ...values,
      eductarPreferences: educators.find(it => it.id.toString() === values.eductarPreferences.toString()),
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
          difficulty: 'LOW',
          ...educatorPreferenceEntity,
          eductarPreferences: educatorPreferenceEntity?.eductarPreferences?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.educatorPreference.home.createOrEditLabel" data-cy="EducatorPreferenceCreateUpdateHeading">
            <Translate contentKey="eduApp.educatorPreference.home.createOrEditLabel">Create or edit a EducatorPreference</Translate>
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
                  id="educator-preference-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.educatorPreference.subject')}
                id="educator-preference-subject"
                name="subject"
                data-cy="subject"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.educatorPreference.difficulty')}
                id="educator-preference-difficulty"
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
              <ValidatedField
                id="educator-preference-eductarPreferences"
                name="eductarPreferences"
                data-cy="eductarPreferences"
                label={translate('eduApp.educatorPreference.eductarPreferences')}
                type="select"
              >
                <option value="" key="0" />
                {educators
                  ? educators.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/educator-preference" replace color="info">
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

export default EducatorPreferenceUpdate;
