import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { IFragmentSet } from 'app/shared/model/fragment-set.model';
import { getEntities as getFragmentSets } from 'app/entities/fragment-set/fragment-set.reducer';
import { IFragmentOrder } from 'app/shared/model/fragment-order.model';
import { getEntity, updateEntity, createEntity, reset } from './fragment-order.reducer';

export const FragmentOrderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fragments = useAppSelector(state => state.fragment.entities);
  const fragmentSets = useAppSelector(state => state.fragmentSet.entities);
  const fragmentOrderEntity = useAppSelector(state => state.fragmentOrder.entity);
  const loading = useAppSelector(state => state.fragmentOrder.loading);
  const updating = useAppSelector(state => state.fragmentOrder.updating);
  const updateSuccess = useAppSelector(state => state.fragmentOrder.updateSuccess);

  const handleClose = () => {
    navigate('/fragment-order');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFragments({}));
    dispatch(getFragmentSets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fragmentOrderEntity,
      ...values,
      fragment: fragments.find(it => it.id.toString() === values.fragment.toString()),
      set: fragmentSets.find(it => it.id.toString() === values.set.toString()),
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
          ...fragmentOrderEntity,
          fragment: fragmentOrderEntity?.fragment?.id,
          set: fragmentOrderEntity?.set?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.fragmentOrder.home.createOrEditLabel" data-cy="FragmentOrderCreateUpdateHeading">
            <Translate contentKey="eduApp.fragmentOrder.home.createOrEditLabel">Create or edit a FragmentOrder</Translate>
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
                  id="fragment-order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.fragmentOrder.order')}
                id="fragment-order-order"
                name="order"
                data-cy="order"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="fragment-order-fragment"
                name="fragment"
                data-cy="fragment"
                label={translate('eduApp.fragmentOrder.fragment')}
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
              <ValidatedField id="fragment-order-set" name="set" data-cy="set" label={translate('eduApp.fragmentOrder.set')} type="select">
                <option value="" key="0" />
                {fragmentSets
                  ? fragmentSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fragment-order" replace color="info">
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

export default FragmentOrderUpdate;
