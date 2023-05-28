import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preferred-activity.reducer';

export const PreferredActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferredActivityEntity = useAppSelector(state => state.preferredActivity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredActivityDetailsHeading">
          <Translate contentKey="eduApp.preferredActivity.detail.title">PreferredActivity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.preferredActivity.title">Title</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.preferredActivity.description">Description</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.description}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eduApp.preferredActivity.type">Type</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.type}</dd>
          <dt>
            <span id="tool">
              <Translate contentKey="eduApp.preferredActivity.tool">Tool</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.tool}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="eduApp.preferredActivity.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.difficulty}</dd>
        </dl>
        <Button tag={Link} to="/preferred-activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-activity/${preferredActivityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredActivityDetail;
