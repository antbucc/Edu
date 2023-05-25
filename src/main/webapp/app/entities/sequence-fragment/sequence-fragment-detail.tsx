import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sequence-fragment.reducer';

export const SequenceFragmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sequenceFragmentEntity = useAppSelector(state => state.sequenceFragment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sequenceFragmentDetailsHeading">
          <Translate contentKey="eduApp.sequenceFragment.detail.title">SequenceFragment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sequenceFragmentEntity.id}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="eduApp.sequenceFragment.order">Order</Translate>
            </span>
          </dt>
          <dd>{sequenceFragmentEntity.order}</dd>
          <dt>
            <Translate contentKey="eduApp.sequenceFragment.sequence">Sequence</Translate>
          </dt>
          <dd>
            {sequenceFragmentEntity.sequences
              ? sequenceFragmentEntity.sequences.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {sequenceFragmentEntity.sequences && i === sequenceFragmentEntity.sequences.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="eduApp.sequenceFragment.fragment">Fragment</Translate>
          </dt>
          <dd>
            {sequenceFragmentEntity.fragments
              ? sequenceFragmentEntity.fragments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {sequenceFragmentEntity.fragments && i === sequenceFragmentEntity.fragments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sequence-fragment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sequence-fragment/${sequenceFragmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SequenceFragmentDetail;
