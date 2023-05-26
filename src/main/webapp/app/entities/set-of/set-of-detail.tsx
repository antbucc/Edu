import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './set-of.reducer';

export const SetOfDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const setOfEntity = useAppSelector(state => state.setOf.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="setOfDetailsHeading">
          <Translate contentKey="eduApp.setOf.detail.title">SetOf</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{setOfEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.setOf.title">Title</Translate>
            </span>
          </dt>
          <dd>{setOfEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.setOf.fragment2">Fragment 2</Translate>
          </dt>
          <dd>{setOfEntity.fragment2 ? setOfEntity.fragment2.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/set-of" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/set-of/${setOfEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SetOfDetail;
