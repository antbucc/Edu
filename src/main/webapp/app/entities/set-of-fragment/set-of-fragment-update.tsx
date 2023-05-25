import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISetOf } from 'app/shared/model/set-of.model';
import { getEntities as getSetOfs } from 'app/entities/set-of/set-of.reducer';
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { ISetOfFragment } from 'app/shared/model/set-of-fragment.model';
import { getEntity, updateEntity, createEntity, reset } from './set-of-fragment.reducer';

export const SetOfFragmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const setOfs = useAppSelector(state => state.setOf.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const setOfFragmentEntity = useAppSelector(state => state.setOfFragment.entity);
  const loading = useAppSelector(state => state.setOfFragment.loading);
  const updating = useAppSelector(state => state.setOfFragment.updating);
  const updateSuccess = useAppSelector(state => state.setOfFragment.updateSuccess);

  const handleClose = () => {
    navigate('/set-of-fragment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSetOfs({}));
    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...setOfFragmentEntity,
      ...values,
      setOf: setOfs.find(it => it.id.toString() === values.setOf.toString()),
      fragment: fragments.find(it => it.id.toString() === values.fragment.toString()),
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
          ...setOfFragmentEntity,
          setOf: setOfFragmentEntity?.setOf?.id,
          fragment: setOfFragmentEntity?.fragment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.setOfFragment.home.createOrEditLabel" data-cy="SetOfFragmentCreateUpdateHeading">
            <Translate contentKey="eduApp.setOfFragment.home.createOrEditLabel">Create or edit a SetOfFragment</Translate>
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
                  id="set-of-fragment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.setOfFragment.order')}
                id="set-of-fragment-order"
                name="order"
                data-cy="order"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="set-of-fragment-setOf"
                name="setOf"
                data-cy="setOf"
                label={translate('eduApp.setOfFragment.setOf')}
                type="select"
              >
                <option value="" key="0" />
                {setOfs
                  ? setOfs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="set-of-fragment-fragment"
                name="fragment"
                data-cy="fragment"
                label={translate('eduApp.setOfFragment.fragment')}
                type="select"
              >
                <option value="" key="0" />
                {fragments
                  ? fragments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/set-of-fragment" replace color="info">
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

export default SetOfFragmentUpdate;
