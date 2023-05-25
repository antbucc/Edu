import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISequence } from 'app/shared/model/sequence.model';
import { getEntities as getSequences } from 'app/entities/sequence/sequence.reducer';
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { ISequenceFragment } from 'app/shared/model/sequence-fragment.model';
import { getEntity, updateEntity, createEntity, reset } from './sequence-fragment.reducer';

export const SequenceFragmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sequences = useAppSelector(state => state.sequence.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const sequenceFragmentEntity = useAppSelector(state => state.sequenceFragment.entity);
  const loading = useAppSelector(state => state.sequenceFragment.loading);
  const updating = useAppSelector(state => state.sequenceFragment.updating);
  const updateSuccess = useAppSelector(state => state.sequenceFragment.updateSuccess);

  const handleClose = () => {
    navigate('/sequence-fragment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSequences({}));
    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sequenceFragmentEntity,
      ...values,
      sequence: sequences.find(it => it.id.toString() === values.sequence.toString()),
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
          ...sequenceFragmentEntity,
          sequence: sequenceFragmentEntity?.sequence?.id,
          fragment: sequenceFragmentEntity?.fragment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.sequenceFragment.home.createOrEditLabel" data-cy="SequenceFragmentCreateUpdateHeading">
            <Translate contentKey="eduApp.sequenceFragment.home.createOrEditLabel">Create or edit a SequenceFragment</Translate>
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
                  id="sequence-fragment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.sequenceFragment.order')}
                id="sequence-fragment-order"
                name="order"
                data-cy="order"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="sequence-fragment-sequence"
                name="sequence"
                data-cy="sequence"
                label={translate('eduApp.sequenceFragment.sequence')}
                type="select"
              >
                <option value="" key="0" />
                {sequences
                  ? sequences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sequence-fragment-fragment"
                name="fragment"
                data-cy="fragment"
                label={translate('eduApp.sequenceFragment.fragment')}
                type="select"
              >
                <option value="" key="0" />
                {fragments
                  ? fragments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sequence-fragment" replace color="info">
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

export default SequenceFragmentUpdate;
