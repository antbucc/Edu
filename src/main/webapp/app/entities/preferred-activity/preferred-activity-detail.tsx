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
            <span id="activity">
              <Translate contentKey="eduApp.preferredActivity.activity">Activity</Translate>
            </span>
          </dt>
          <dd>{preferredActivityEntity.activity}</dd>
          <dt>
            <Translate contentKey="eduApp.preferredActivity.educatorPreference">Educator Preference</Translate>
          </dt>
          <dd>{preferredActivityEntity.educatorPreference ? preferredActivityEntity.educatorPreference.id : ''}</dd>
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
