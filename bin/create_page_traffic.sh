#!/usr/bin/env bash

BASE_URL="http://localhost:8080/api"

function spaceship_api_traffic() {
  local url="$BASE_URL/spaceship"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=1&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=50"
}

function faction_api_traffic() {
  local url="$BASE_URL/faction"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=1&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=50"
}

function planet_api_traffic() {
  local url="$BASE_URL/planet"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=1&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=50"
}

function person_api_traffic() {
  local url="$BASE_URL/person"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=25"
  curl -s -H "$headers" "$url?page=1&pageSize=25"
  curl -s -H "$headers" "$url?page=0&pageSize=50"
}

echo "Calling to APIs..."

spaceship_api_traffic
faction_api_traffic
planet_api_traffic
person_api_traffic

echo "Done!"

